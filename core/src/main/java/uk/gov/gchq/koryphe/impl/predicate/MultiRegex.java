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

package uk.gov.gchq.koryphe.impl.predicate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import uk.gov.gchq.koryphe.Since;
import uk.gov.gchq.koryphe.Summary;
import uk.gov.gchq.koryphe.predicate.KoryphePredicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A {@link MultiRegex} is a {@link KoryphePredicate} that returns true
 * if an input string matches a provided multiregex pattern, false otherwise.
 * Multiple patterns are passed in as an array.
 */
@Since("1.0.0")
@Summary("Checks if a string matches at least one pattern")
public class MultiRegex extends KoryphePredicate<String> {
    private Pattern[] patterns;

    public MultiRegex() {
        this((Pattern[]) null);
    }

    public MultiRegex(final Pattern... patterns) {
        setPatterns(patterns);
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    @JsonProperty("value")
    public Pattern[] getPatterns() {
        return Arrays.copyOf(patterns, patterns.length);
    }

    public void setPatterns(final Pattern[] patterns) {
        if (null != patterns) {
            this.patterns = Arrays.copyOf(patterns, patterns.length);
        } else {
            this.patterns = new Pattern[0];
        }
    }

    @Override
    public boolean test(final String input) {
        if (null == input || input.getClass() != String.class) {
            return false;
        }
        for (final Pattern pattern : patterns) {
            if (pattern.matcher(input).matches()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (null == obj || !getClass().equals(obj.getClass())) {
            return false;
        }

        final MultiRegex that = (MultiRegex) obj;

        return new EqualsBuilder()
                .append(patternsToStrings(patterns), patternsToStrings(that.patterns))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(patternsToStrings(patterns))
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("patterns", patterns)
                .toString();
    }

    /**
     * Utility method to convert an array of {@link java.util.regex.Pattern}s to
     * and array of {@link String}s.
     * <p>
     * This is required since the Pattern class does not override {@link Object#equals(Object)}
     * or {@link Object#hashCode()}
     *
     * @param patterns an array of Patterns to convert
     * @return an array of Strings representing the regex pattern
     */
    private String[] patternsToStrings(final Pattern[] patterns) {
        final List<String> strings = new ArrayList<>(patterns.length);

        for (final Pattern pattern : patterns) {
            strings.add(pattern.toString());
        }

        return strings.toArray(new String[]{});
    }
}
