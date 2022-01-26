import "./App.css";
import axios from "axios";
import { useEffect, useState } from "react";

function App() {
  const [authenticated, setAuthenticated] = useState(false);
  const [user, setUser] = useState({});

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/info", {
          withCredentials: true,
          maxRedirects: 0,
          headers: {
            "Content-type": "application/json",
          },
        });

        if (response.status !== 200) {
          window.location.replace("http://localhost:8080/login");
        }

        console.log(response.data);
        setUser(response.data);
        setAuthenticated(true);
      } catch (err) {
        console.error(err);
        window.location.replace("http://localhost:8080/login");
      }
    };
    fetchUserInfo();
  }, []);

  const logout = () => {
    window.location.replace("http://localhost:8080/slo");
  };

  return (
    <>
      {authenticated && (
        <div>
          <p>User is authenticated: {user.user}</p>
          <button type="button" onClick={logout}>Logout</button>
        </div>
      )}
      {!authenticated && (
        <div>
          <p>Verifying authentication</p>
        </div>
      )}
    </>
  );
}

export default App;
