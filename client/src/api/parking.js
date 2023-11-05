import { extractData } from "./helpers";
import request from "./request";

const urls = {
  root: "/parking",
  get fetchAll() {
    return this.root + "/all";
  },
  fetchPrice(id) {
    return `${this.root}/price/${id}`;
  },
};

const fetchAll = async () => {
  const result = await request.get(urls.fetchAll).then(extractData);
  return result;
};

const fetchPrice = (id) => {
  console.log(id);
  const result = request.get(urls.fetchPrice(id)).then(extractData);
  return result;
};

export default {
  fetchAll,
  fetchPrice,
};
