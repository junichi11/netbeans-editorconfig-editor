/*
 * The MIT License
 *
 * Copyright 2015 junichi11.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.junichi11.netbeans.modules.editorconfig.editor.csl;

import com.junichi11.netbeans.modules.editorconfig.editor.parser.ECParserResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr4.EditorConfigParser;
import org.antlr4.EditorConfigParserBaseVisitor;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.api.StructureItem;
import org.netbeans.modules.csl.api.StructureScanner;
import org.netbeans.modules.csl.spi.ParserResult;

/**
 *
 * @author junichi11
 */
public class EditorConfigStructureScanner implements StructureScanner {

    @Override
    public List<? extends StructureItem> scan(ParserResult parserResult) {
        ECParserResult result = (ECParserResult) parserResult;
        StructureVisitor visitor = new StructureVisitor();
        visitor.visit(result.getRoot());
        return visitor.getElements();
    }

    @Override
    public Map<String, List<OffsetRange>> folds(ParserResult parserResult) {
        return Collections.emptyMap();
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    private static class StructureVisitor extends EditorConfigParserBaseVisitor<Void> {

        private final ArrayList<StructureItem> sections = new ArrayList<>();
        private final ArrayList<StructureItem> properties = new ArrayList<>();
        private String sectionName;
        private int sectionStart;
        private int sectionEnd;
        private int propertyStart;
        private int propertyEnd;
        private String propertyKey;
        private String propertyValue;
        private int childCount;
        private int currentChildCount;

        @Override
        public Void visitSection(EditorConfigParser.SectionContext section) {
            childCount = section.getChildCount();
            return super.visitSection(section);
        }

        @Override
        public Void visitPropertyStatement(EditorConfigParser.PropertyStatementContext property) {
            propertyStart = property.getStart().getStartIndex();
            propertyEnd = property.getStop().getStopIndex();
            currentChildCount++;
            return super.visitPropertyStatement(property);
        }

        @Override
        public Void visitPropertyValue(EditorConfigParser.PropertyValueContext ctx) {
            propertyValue = ctx.getText();
            if (propertyKey != null && !propertyKey.isEmpty() && propertyValue != null) {
                properties.add(new EditorConfigPropertyStructureItem(propertyKey, propertyValue, propertyStart, propertyEnd));
            }
            propertyKey = null;
            propertyValue = null;
            propertyStart = 0;
            propertyEnd = 0;

            if (childCount == currentChildCount) {
                if (sectionName != null) {
                    ArrayList<StructureItem> children = new ArrayList<>(properties);
                    sections.add(new EditorConfigSectionStructureItem(sectionName, sectionStart, sectionEnd, children));
                }
                sectionName = null;
                sectionStart = 0;
                sectionEnd = 0;
                properties.clear();
                currentChildCount = 0;
            }
            return super.visitPropertyValue(ctx);
        }

        @Override
        public Void visitPropertyKey(EditorConfigParser.PropertyKeyContext ctx) {
            propertyKey = ctx.getText();
            return super.visitPropertyKey(ctx);
        }

        @Override
        public Void visitSectionHeader(EditorConfigParser.SectionHeaderContext sectionHeader) {
            sectionStart = sectionHeader.getStart().getStartIndex();
            sectionEnd = sectionHeader.getStop().getStopIndex();
            TerminalNode sectionNode = sectionHeader.SECTION_NAME();
            sectionName = sectionNode != null ? sectionNode.getText() : ""; // NOI18N
            currentChildCount++;
            return super.visitSectionHeader(sectionHeader);
        }

        public List<StructureItem> getElements() {
            return sections;
        }

    }

}
