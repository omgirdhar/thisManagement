function authFetch(url, options = {}) {
  options.headers = options.headers || {};
  options.headers["X-Requested-With"] = "XMLHttpRequest";

  if (!options.headers["Content-Type"] && options.body) {
    options.headers["Content-Type"] = "application/json";
  }

  return fetch(url, options)
    .then(res => {

      if (res.status === 401) {
        window.location.href = "/login?expired";
        throw new Error("Unauthorized");
      }

      if (!res.ok) {
        throw new Error("Request failed");
      }

      if (res.status === 204) return null;

      const contentType = res.headers.get("content-type");
      if (contentType && contentType.includes("application/json")) {
        return res.json();
      }

      return null;
    });
}