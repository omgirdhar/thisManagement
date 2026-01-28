function authFetch(url, options = {}) {
  options.headers = options.headers || {};
  options.headers["X-Requested-With"] = "XMLHttpRequest";

  // Default content-type for JSON requests
  if (!options.headers["Content-Type"] && options.body) {
    options.headers["Content-Type"] = "application/json";
  }

  return fetch(url, options)
    .then(res => {
      if (res.status === 401) {
        window.location.href = "/login?expired";
        return;
      }

      // No content
      if (res.status === 204) {
        return null;
      }

      // Try to read JSON only if present
      const contentType = res.headers.get("content-type");
      if (contentType && contentType.includes("application/json")) {
        return res.json();
      }

      return null;
    });
}