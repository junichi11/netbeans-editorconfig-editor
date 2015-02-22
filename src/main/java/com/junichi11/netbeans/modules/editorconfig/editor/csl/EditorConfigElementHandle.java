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
import java.util.Collections;
import java.util.Set;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.Modifier;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;
import org.openide.filesystems.FileObject;

/**
 *
 * @author junichi11
 */
public class EditorConfigElementHandle implements ElementHandle {

    private final String name;
    private final int startPosition;
    private final int endPosition;
    private final ElementKind elementKind;

    public EditorConfigElementHandle(String name, int startPosition, int endPosition, ElementKind elementKind) {
        this.name = name;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.elementKind = elementKind;
    }

    @Override
    public FileObject getFileObject() {
        return null;
    }

    @Override
    public String getMimeType() {
        return ECTokenId.EDITORCONFIG_MIME_TYPE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIn() {
        return null;
    }

    @Override
    public ElementKind getKind() {
        return elementKind;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return Collections.emptySet();
    }

    @Override
    public boolean signatureEquals(ElementHandle elementHandle) {
        if (elementHandle instanceof EditorConfigElementHandle) {
            return name.equals(((EditorConfigElementHandle) elementHandle).name);
        }
        return false;
    }

    @Override
    public OffsetRange getOffsetRange(ParserResult pr) {
        return new OffsetRange(startPosition, endPosition);
    }

}
