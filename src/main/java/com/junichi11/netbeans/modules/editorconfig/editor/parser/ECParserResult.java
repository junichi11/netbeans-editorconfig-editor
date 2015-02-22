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
package com.junichi11.netbeans.modules.editorconfig.editor.parser;

import java.util.Collections;
import java.util.List;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr4.EditorConfigErrorListener;
import org.antlr4.EditorConfigParser;
import org.antlr4.SyntaxError;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.ParseException;

/**
 *
 * @author junichi11
 */
public class ECParserResult extends ParserResult {

    private final EditorConfigParser parser;
    private boolean valid = true;
    private final ParseTree root;

    public ECParserResult(Snapshot snapshot, EditorConfigParser parser, ParseTree root) {
        super(snapshot);
        this.parser = parser;
        this.root = root;
    }

    public EditorConfigParser getEditorConfigParser() throws ParseException {
        if (!valid) {
            throw new ParseException();
        }
        return parser;
    }

    public ParseTree getRoot() {
        return root;
    }

    @Override
    protected void invalidate() {
        valid = false;
    }

    @Override
    public List<? extends Error> getDiagnostics() {
        return Collections.emptyList();
    }

    public List<SyntaxError> getErrors() {
        List<? extends ANTLRErrorListener> errorListeners = parser.getErrorListeners();
        for (ANTLRErrorListener errorListener : errorListeners) {
            if (errorListener instanceof EditorConfigErrorListener) {
                EditorConfigErrorListener ecErrorListener = (EditorConfigErrorListener) errorListener;
                return ecErrorListener.getErrorMessages();
            }
        }
        return Collections.emptyList();
    }

}
