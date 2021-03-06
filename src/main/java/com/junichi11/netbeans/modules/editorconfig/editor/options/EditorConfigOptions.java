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
package com.junichi11.netbeans.modules.editorconfig.editor.options;

import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 *
 * @author junichi11
 */
public class EditorConfigOptions {

    private static final EditorConfigOptions INSTANCE = new EditorConfigOptions();
    private static final String EDITOR_CONFIG = "editorconfig"; // NOI18N
    private static final String LINE_COMMENT_PREFIX = "comment.prefix"; // NOI18N

    private EditorConfigOptions() {
    }

    public static EditorConfigOptions getInstance() {
        return INSTANCE;
    }

    /**
     * Get a comment prefix. Can use "#" or ";".
     *
     * @return a comment prefix
     */
    public String getLineCommentPrefix() {
        return getPreferences().get(LINE_COMMENT_PREFIX, "#"); // NOI18N
    }

    public void setLineCommentPrefix(String lineCommentPrefix) {
        getPreferences().put(LINE_COMMENT_PREFIX, lineCommentPrefix);
    }

    private Preferences getPreferences() {
        return NbPreferences.forModule(EditorConfigOptions.class).node(EDITOR_CONFIG);
    }
}
