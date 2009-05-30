/*
 * Copyright 2006-2009 the original author or authors.
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
package org.springframework.batch.core.step.item;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Dan Garrette
 * @since 2.0.1
 */
public abstract class ExceptionThrowingItemHandlerStub<T> {

	private Collection<T> failures = Collections.emptyList();

	private boolean runtimeException = false;

	public void setFailures(T... failures) {
		this.failures = Arrays.asList(failures);
	}

	public void setRuntimeException(boolean runtimeException) {
		this.runtimeException = runtimeException;
	}

	public void clear() {
		failures.clear();
	}

	protected void checkFailure(T item) throws Exception {
		if (isFailure(item)) {
			if (runtimeException) {
				throw new SkippableRuntimeException("Intended Failure");
			}
			else {
				throw new SkippableException("Intended Failure");
			}
		}
	}

	protected boolean isFailure(T item) {
		return this.failures.contains(item);
	}
}
