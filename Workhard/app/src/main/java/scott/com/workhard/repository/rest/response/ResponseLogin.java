package scott.com.workhard.repository.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import scott.com.workhard.models.Token;
import scott.com.workhard.models.User;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 7/17/16.
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
public class ResponseLogin {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("token")
    @Expose
    private Token token;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
