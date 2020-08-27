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

package uk.gov.gchq.koryphe.tuple.predicate;

import uk.gov.gchq.koryphe.tuple.n.Tuple5;

public abstract class KoryphePredicate5<T, U, V, W, X> extends KoryphePredicateN<Tuple5<T, U, V, W, X>> {
    public abstract boolean test(T t, U u, V v, W w, X x);

    @Override
    protected boolean delegateTest(final Tuple5<T, U, V, W, X> tuple) {
        return test(tuple.get0(), tuple.get1(), tuple.get2(), tuple.get3(), tuple.get4());
    }
}
