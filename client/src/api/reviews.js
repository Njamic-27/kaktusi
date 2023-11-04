import { extractData } from './helpers';
import request from './request';

const urls = {
  root: '/reviews',
  remove(id) {
    return `${this.root}/${id}`;
  },
};

const create = review => {
  return request.post(urls.root, review);
};

const remove = id => {
  return request.delete(urls.remove(id));
};

export default {
  create,
  remove,
};
