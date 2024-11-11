import "../assets/styles/login.scss";
import {Link, useNavigate} from "react-router-dom";
import {useState} from "react";
import axios from "axios";

const Login = (props) => {

    const navigate = useNavigate();
    const [userId, setUserId] = useState("");
    const [password, setPassword] = useState("");

    const [isInvalid, setIsInvalid] = useState(false);
    const [isError, setIsError] = useState(false);

    const handleLogin = async (event) => {
        event.preventDefault();
        console.log("----------Login----------");

        axios({
            method: "POST",
            url: "http://localhost:8080/auth/signin",
            data: {
                "username": userId,
                "password": password,
            },

        })
            .then(response => setToken(response.data))
            .catch(error => setError(error.response.data.error));
    }

    const setToken = (data) => {
        localStorage.clear();
        localStorage.setItem('accessToken', data["accessToken"]);
        localStorage.setItem('refreshToken', data["refreshToken"]);
        props.setReload(!(props.reload));
        setIsInvalid(false);
        setIsError(false)
        navigate("/");

    }

    const setError = (error) => {
        console.log(error);
        if (error === "id password wrong") {
            setIsInvalid(true);
            setIsError(false);
        } else {
            setIsError(true);
            setIsInvalid(false);
        }
    }

    return (
        <div className={"container"}>
            <div className={"box"}>
                <form className={"login-form"} onSubmit={handleLogin}>
                    <label htmlFor="id">아이디</label>
                    <input
                        onChange={e => setUserId(e.target.value)}
                        type="text" name="id" id="id"/>

                    <label htmlFor="password1">비밀번호</label>
                    <input
                        onChange={e => setPassword(e.target.value)}
                        type="password" name="password1" id="password1"/>
                    {isInvalid ? <span className={"warning"}>아이디와 비밀번호를 확인해주세요.</span> : null}
                    {isError ? <span className={"warning"}>알수 없는 에러입니다</span> : null}

                    <button className={"main-button"} onClick={handleLogin}>로그인</button>

                    <Link to="/join">
                        <button>회원가입</button>
                    </Link>


                </form>
            </div>
        </div>
    )
}

export default Login;