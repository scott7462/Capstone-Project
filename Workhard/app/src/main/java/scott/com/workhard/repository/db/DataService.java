package scott.com.workhard.repository.db;

import rx.Observable;
import scott.com.workhard.models.User;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 7/18/16.
 * <p>
 * Copyright (C) 2015 The Android Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 *  @see <a href = "http://www.aprenderaprogramar.com" /> http://www.apache.org/licenses/LICENSE-2.0 </a>
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@SuppressWarnings("SameReturnValue")
interface DataService {

    Observable<User> getUserById(String id);

    Observable<User> saveUser(User user);

    Observable<Void> removeUser(User user);

}