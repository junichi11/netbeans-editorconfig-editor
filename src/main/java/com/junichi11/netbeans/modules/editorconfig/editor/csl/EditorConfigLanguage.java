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

import com.junichi11.netbeans.modules.editorconfig.editor.api.lexer.ECTokenId;
import com.junichi11.netbeans.modules.editorconfig.editor.options.EditorConfigOptions;
import com.junichi11.netbeans.modules.editorconfig.editor.parser.ECParser;
import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.api.StructureScanner;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;
import org.netbeans.modules.parsing.spi.Parser;

/**
 *
 * @author junichi11
 */
@LanguageRegistration(mimeType = ECTokenId.EDITORCONFIG_MIME_TYPE, useMultiview = true)
public class EditorConfigLanguage extends DefaultLanguageConfig {

    @Override
    public Language getLexerLanguage() {
        return ECTokenId.language();
    }

    @Override
    public String getDisplayName() {
        return "EditorConfig"; // NOI18N
    }

    @Override
    public Parser getParser() {
        return new ECParser();
    }

    @Override
    public String getLineCommentPrefix() {
        // # or ;
        return EditorConfigOptions.getInstance().getLineCommentPrefix();
    }

    @Override
    public StructureScanner getStructureScanner() {
        return new EditorConfigStructureScanner();
    }

    @Override
    public boolean hasStructureScanner() {
        return true;
    }

    @Override
    public String getPreferredExtension() {
        return "editorconfig"; // NOI18N
    }

}
