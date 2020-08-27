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

package uk.gov.gchq.koryphe.tuple.function;

import uk.gov.gchq.koryphe.tuple.n.Tuple2;

public abstract class KorypheFunction2<T, U, R> extends KorypheFunctionN<Tuple2<T, U>, R> {
    public abstract R apply(T t, U u);

    public R delegateApply(final Tuple2<T, U> tuple) {
        return apply(tuple.get0(), tuple.get1());
    }
}
