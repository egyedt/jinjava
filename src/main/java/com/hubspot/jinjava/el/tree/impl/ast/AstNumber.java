/*
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
package com.hubspot.jinjava.el.tree.impl.ast;

import com.hubspot.jinjava.el.tree.Bindings;
import jakarta.el.ELContext;

public final class AstNumber extends AstLiteral {
    private final Number value;

    public AstNumber(Number value) {
        this.value = value;
    }

    @Override
    public Object eval(Bindings bindings, ELContext context) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public void appendStructure(StringBuilder b, Bindings bindings) {
        b.append(value);
    }
}
