import "../src/assets/styles/setting.css";
import "../src/assets/styles/reset.css";
import {QueryClient, QueryClientProvider} from "react-query";
import Router from "./components/Router";
import {useEffect, useState} from "react";

function App() {
    // const [nickName, setNickName] = useState('');
    const [reload, setReload] = useState(false);
    const [isAuth, setIsAuth] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem('accessToken');
        token ? setIsAuth(true) : setIsAuth(false);
    }, [reload]);

    const queryClient = new QueryClient();
    return (
        <QueryClientProvider client={queryClient}>
            <div className="App">
                <Router isAuth={isAuth} reload={reload} setReload={setReload}/>
            </div>
        </QueryClientProvider>
    );
}

export default App;
