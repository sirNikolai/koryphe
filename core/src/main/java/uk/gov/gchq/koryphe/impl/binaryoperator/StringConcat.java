/*
 * Copyright 2017-2020 Crown Copyright
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

package uk.gov.gchq.koryphe.impl.binaryoperator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import uk.gov.gchq.koryphe.Since;
import uk.gov.gchq.koryphe.Summary;
import uk.gov.gchq.koryphe.binaryoperator.KorypheBinaryOperator;

/**
 * A <code>StringConcat</code> is a {@link KorypheBinaryOperator} that takes in
 * {@link String}s and concatenates them together. The default separator is a comma, you can set a custom
 * separator using setSeparator(String).
 */
@Since("1.0.0")
@Summary("Concatenates 2 strings")
public class StringConcat extends KorypheBinaryOperator<String> {
    private static final String DEFAULT_SEPARATOR = ",";
    private String separator = DEFAULT_SEPARATOR;

    public StringConcat() {
        // required for serialisation
    }

    public StringConcat(final String separator) {
        this.separator = separator;
    }

    @Override
    public String _apply(final String a, final String b) {
        return a + separator + b;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(final String separator) {
        this.separator = separator;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (null == obj || !getClass().equals(obj.getClass())) {
            return false;
        }

        final StringConcat that = (StringConcat) obj;
        return new EqualsBuilder()
                .append(separator, that.separator)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(separator)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("separator", separator)
                .toString();
    }
}
