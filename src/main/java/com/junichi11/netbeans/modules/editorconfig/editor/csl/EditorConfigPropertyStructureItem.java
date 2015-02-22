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

import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.swing.ImageIcon;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.HtmlFormatter;
import org.netbeans.modules.csl.api.Modifier;
import org.netbeans.modules.csl.api.StructureItem;

/**
 *
 * @author junichi11
 */
public class EditorConfigPropertyStructureItem implements StructureItem {

    private final String key;
    private final String value;
    private final int startPosition;
    private final int endPosition;

    public EditorConfigPropertyStructureItem(String key, String value, int startPosition, int endPosition) {
        this.key = key;
        this.value = value;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    @Override
    public String getName() {
        return key;
    }

    @Override
    public String getSortText() {
        return key;
    }

    @Override
    public String getHtml(HtmlFormatter formatter) {
        return String.format("%s : %s", key, value); // NOI18N
    }

    @Override
    public ElementHandle getElementHandle() {
        return new EditorConfigElementHandle(key, startPosition, endPosition, ElementKind.PROPERTY);
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.PROPERTY;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return Collections.emptySet();
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public List<? extends StructureItem> getNestedItems() {
        return Collections.emptyList();
    }

    @Override
    public long getPosition() {
        return startPosition;
    }

    @Override
    public long getEndPosition() {
        return endPosition;
    }

    @Override
    public ImageIcon getCustomIcon() {
        return null;
    }

}
