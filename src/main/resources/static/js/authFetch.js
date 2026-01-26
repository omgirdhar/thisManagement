function authFetch(url, options = {}) {
  options.headers = options.headers || {};
  options.headers["X-Requested-With"] = "XMLHttpRequest";

  return fetch(url, options)
    .then(res => {
      if (res.status === 401) {
        window.location.href = "/login?expired";
        return;
      }
      return res.json();
    });
}
