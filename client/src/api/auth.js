import { extractData } from "./helpers";
import request from "./request";

const urls = {
  root: "/users",
  get login() {
    return this.root + "/login";
  },
  get register() {
    return this.root + "/register";
  },
  get logout() {
    return this.root + "/logout";
  },
};

const login = async (username, password) => {
  const result = await request
    .post(urls.login, { username, password })
    .then(extractData);
  return result;
};

const register = (username, password) => {
  const result = request
    .post(urls.register, { username, password })
    .then(extractData);
  console.log(result);
  return result;
};

const logout = () => {
  return request.post(urls.logout).then(extractData);
};

export default {
  login,
  register,
  logout,
};
