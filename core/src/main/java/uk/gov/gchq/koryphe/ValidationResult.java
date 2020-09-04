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

package uk.gov.gchq.koryphe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class ValidationResult {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationResult.class);

    private boolean isValid = true;
    private Set<String> errors;

    public ValidationResult() {
    }

    public ValidationResult(final String errorMsg) {
        addError(errorMsg);
    }

    public void addError(final String msg) {
        isValid = false;
        if (null == errors) {
            errors = new LinkedHashSet<>();
        }
        errors.add(msg);
    }

    public void add(final ValidationResult validationResult) {
        add(validationResult, null);
    }

    public void add(final ValidationResult validationResult, final String errorMessage) {
        if (!validationResult.isValid()) {
            isValid = false;
            if (null == errors) {
                errors = new LinkedHashSet<>(validationResult.getErrors());
            } else {
                errors.addAll(validationResult.getErrors());
            }

            if (null != errorMessage) {
                errors.add(errorMessage);
            }
        }
    }

    public boolean isValid() {
        return isValid;
    }

    public Set<String> getErrors() {
        if (null == errors) {
            return Collections.emptySet();
        }

        return errors;
    }

    public void logErrors() {
        getErrors().forEach(LOGGER::error);
    }

    public void logWarns() {
        getErrors().forEach(LOGGER::warn);
    }

    public void logInfo() {
        getErrors().forEach(LOGGER::info);
    }

    public void logDebug() {
        getErrors().forEach(LOGGER::debug);
    }

    public void logTrace() {
        getErrors().forEach(LOGGER::trace);
    }

    @JsonIgnore
    public String getErrorString() {
        return "Validation errors: " + System.lineSeparator() + StringUtils.join(getErrors(), System.lineSeparator());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ValidationResult that = (ValidationResult) o;

        return new EqualsBuilder()
                .append(isValid, that.isValid)
                .append(errors, that.errors)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 67)
                .append(isValid)
                .append(errors)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("isInputValid", isValid)
                .append("errors", errors)
                .build();
    }
}
