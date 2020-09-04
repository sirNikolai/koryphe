/*
 * Copyright 2020 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.koryphe.impl.function;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import uk.gov.gchq.koryphe.Since;
import uk.gov.gchq.koryphe.Summary;
import uk.gov.gchq.koryphe.function.KorypheFunction;

/**
 * A {@code StringRegexReplace} is a {@link java.util.function.Function} which takes an input string and applies the
 * provided regular expression to find matching substrings to replace. It then replaces all of these matches with the
 * provided replacement string.
 *
 * @see StringReplace
 */
@Since("1.9.0")
@Summary("Replace all portions of a string which match a regular expression.")
public class StringRegexReplace extends KorypheFunction<String, String> {

    private String regex;
    private String replacement;

    public StringRegexReplace() {
    }

    public StringRegexReplace(final String regex, final String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String apply(final String input) {
        if (null == input) {
            return null;
        }

        return input.replaceAll(regex, replacement);
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(final String regex) {
        this.regex = regex;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(final String replacement) {
        this.replacement = replacement;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!super.equals(o)) {
            return false; // Does exact equals and class checking
        }

        StringRegexReplace that = (StringRegexReplace) o;
        return new EqualsBuilder()
                .append(regex, that.regex)
                .append(replacement, that.replacement)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(83, 29)
                .appendSuper(super.hashCode())
                .append(regex)
                .append(replacement)
                .toHashCode();
    }
}
