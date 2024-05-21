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
  fetchReservations(id) {
    return this.root + `/reservations/${id}`;
  },
};

const fetchBalance = async (id) => {
  const result = await request.get(urls.fetchBalance(id)).then(extractData);
  return result;
};

const updateBalance = (id, balance) => {
  const result = request
    .put(urls.updateBalance(id), { balance })
    .then(extractData);
  return result;
};

const fetchReservations = async (id) => {
  const result = await request.get(urls.fetchReservations).then(extractData);
  return result;
};

export default {
  fetchBalance,
  updateBalance,
  fetchReservations,
};
