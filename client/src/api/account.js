import { extractData } from "./helpers";
import request from "./request";

const urls = {
  root: "/account",
  fetchBalance(id) {
    return `${this.root}/balance/${id}`;
  },
  updateBalance(id) {
    return `${this.root}/update/${id}`;
  },
};

const fetchBalance = async (id) => {
  const result = await request.get(urls.fetchBalance(id)).then(extractData);
  return result;
};

const updateBalance = async (id, balance) => {
  const result = await request
    .put(urls.updateBalance(id), { balance })
    .then(extractData);
  return result;
};

export default {
  fetchBalance,
  updateBalance,
};
