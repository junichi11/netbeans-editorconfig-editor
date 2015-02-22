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
package com.junichi11.netbeans.modules.editorconfig.editor;

import com.junichi11.netbeans.modules.editorconfig.editor.api.lexer.ECTokenId;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author junichi11
 */
@ServiceProvider(service = MIMEResolver.class)
public class EditorConfigMIMEResolver extends MIMEResolver {

    public EditorConfigMIMEResolver() {
        super(ECTokenId.EDITORCONFIG_MIME_TYPE);
    }

    @Override
    public String findMIMEType(FileObject fo) {
        if (fo != null) {
            String name = fo.getNameExt();
            if (!fo.isFolder() && ".editorconfig".equals(name)) { // NOI18N
                return ECTokenId.EDITORCONFIG_MIME_TYPE;
            }
        }
        return null;
    }

}
