import { extractData } from './helpers';
import request from './request';

const urls = {
  root: '/requests',

  get insertRequest() {
    return `${this.root}/insert-request`;
  },
  get deleteRequest() {
    return `${this.root}/delete-request`;
  },
};

const insertRequest = r => {
  return request.post(urls.insertRequest, r);
};

const deleteRequest = r => {
  return request.post(urls.deleteRequest, r);
};

export default {
  insertRequest,
  deleteRequest,
};
