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
package com.junichi11.netbeans.modules.editorconfig.editor.lexer;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.misc.Interval;
import org.netbeans.spi.lexer.LexerInput;

public class AntlrCharStream implements CharStream {

    private final LexerInput input;
    private final String name;
    private int index = 0;
    private int markDepth = 0;

    public AntlrCharStream(LexerInput input, String name) {
        this.input = input;
        this.name = name;
    }

    @Override
    public String getText(Interval interval) {
        return input.readText().toString();
    }

    @Override
    public void consume() {
        int character = read();
        if (character == EOF) {
            backup(1);
            throw new IllegalStateException("Attempting to consume EOF");
        }
        index++;
    }

    @Override
    public int LA(int lookaheadAmount) {
        if (lookaheadAmount < 0) {
            return lookBack(-lookaheadAmount);
        } else if (lookaheadAmount > 0) {
            return lookAhead(lookaheadAmount);
        } else {
            return 0; //Behaviour is undefined when lookaheadAmount == 0
        }
    }

    private int lookBack(int amount) {
        backup(amount);
        int character = read();
        for (int i = 1; i < amount; i++) {
            read();
        }
        return character;
    }

    private int lookAhead(int amount) {
        int character = 0;
        for (int i = 0; i < amount; i++) {
            character = read();
        }
        backup(amount);
        return character;
    }

    @Override
    public int mark() {
        return ++markDepth;
    }

    @Override
    public void release(int marker) {
        // unwind any other markers made after m and release m
        markDepth = marker;
        // release this marker
        markDepth--;
    }

    @Override
    public void seek(int index) {
        if (index < 0) {
            throw new IllegalArgumentException(String.format("Invalid index (%s < 0)", index));
        }

        if (index < this.index) {
            backup(this.index - index);
            this.index = index;
            return;
        }

        // seek forward, consume until p hits index
        while (this.index < index) {
            consume();
        }
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Stream size unknown");
    }

    @Override
    public String getSourceName() {
        return name;
    }

    private int read() {
        int result = input.read();
        if (result == LexerInput.EOF) {
            return EOF;
        } else {
            return result;
        }
    }

    private void backup(int count) {
        input.backup(count);
    }
}
