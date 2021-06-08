/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hudi.sink.bootstrap.aggregate;

import org.apache.flink.api.common.functions.AggregateFunction;

/**
 * Aggregate Function that accumulates the loaded task number of function {@link org.apache.hudi.sink.bootstrap.BootstrapFunction}.
 */
public class BootstrapAggFunc implements AggregateFunction<Integer, BootstrapAccumulator, Integer> {
  public static final String NAME = BootstrapAggFunc.class.getSimpleName();

  @Override
  public BootstrapAccumulator createAccumulator() {
    return new BootstrapAccumulator();
  }

  @Override
  public BootstrapAccumulator add(Integer taskId, BootstrapAccumulator bootstrapAccumulator) {
    bootstrapAccumulator.update(taskId);
    return bootstrapAccumulator;
  }

  @Override
  public Integer getResult(BootstrapAccumulator bootstrapAccumulator) {
    return bootstrapAccumulator.readyTaskNum();
  }

  @Override
  public BootstrapAccumulator merge(BootstrapAccumulator bootstrapAccumulator, BootstrapAccumulator acc) {
    return bootstrapAccumulator.merge(acc);
  }
}