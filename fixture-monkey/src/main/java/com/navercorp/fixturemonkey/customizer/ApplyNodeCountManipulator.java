/*
 * Fixture Monkey
 *
 * Copyright (c) 2021-present NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.fixturemonkey.customizer;

import java.util.Objects;

import com.navercorp.fixturemonkey.tree.ObjectNode;

public final class ApplyNodeCountManipulator implements NodeManipulator {
	private final NodeManipulator nodeManipulator;
	private int count;

	public ApplyNodeCountManipulator(NodeManipulator nodeManipulator, int count) {
		this.nodeManipulator = nodeManipulator;
		this.count = count;
	}

	@Override
	public void manipulate(ObjectNode objectNode) {
		if (count > 0) {
			count--;
			nodeManipulator.manipulate(objectNode);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ApplyNodeCountManipulator that = (ApplyNodeCountManipulator)obj;
		return Objects.equals(nodeManipulator, that.nodeManipulator);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nodeManipulator);
	}
}
