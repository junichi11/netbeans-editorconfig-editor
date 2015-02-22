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

import com.junichi11.netbeans.modules.editorconfig.editor.api.lexer.ECTokenId;
import org.antlr.v4.runtime.misc.IntegerStack;
import org.antlr4.EditorConfigLexer;
import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 * @author junichi11
 */
public class ECLexer implements Lexer<ECTokenId> {

    private final LexerRestartInfo<ECTokenId> info;
    private final EditorConfigLexer lexer;

    private ECLexer(LexerRestartInfo<ECTokenId> info) {
        this.info = info;
        AntlrCharStream charStream = new AntlrCharStream(info.input(), "EditorConfig"); // NOI18N
        lexer = new EditorConfigLexer(charStream);
        lexer.removeErrorListeners();
        // TODO add listener
        LexerState lexerMode = (LexerState) info.state();
        if (lexerMode != null) {
            lexer._mode = lexerMode.Mode;
            lexer._modeStack.addAll(lexerMode.Stack);
        }
    }

    public static synchronized ECLexer create(LexerRestartInfo<ECTokenId> info) {
        return new ECLexer(info);
    }

    @Override
    public Token<ECTokenId> nextToken() {
        org.antlr.v4.runtime.Token token = lexer.nextToken();
        int type = token.getType();
        ECTokenId tokenId = ECTokenId.toEnum(type);
        assert tokenId != null;
        if (tokenId != ECTokenId.EOF) {
            return info.tokenFactory().createToken(tokenId);
        }
        return null;
    }

    @Override
    public Object state() {
        return new LexerState(lexer._mode, lexer._modeStack);
    }

    @Override
    public void release() {
    }

    // If mode is used, keep state of it
    // http://stackoverflow.com/questions/23887888/antlr4-based-lexer-loses-syntax-hightlighting-during-typing-on-netbeans
    private static class LexerState {

        public int Mode = -1;
        public IntegerStack Stack = null;

        public LexerState(int mode, IntegerStack stack) {
            Mode = mode;
            Stack = new IntegerStack(stack);
        }
    }
}
