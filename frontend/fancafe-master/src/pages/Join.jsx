import "../assets/styles/login.scss";
import {useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";

const Join = ({props}) => {
    const navigate = useNavigate();

    const [userId, setUserId] = useState("");
    const [password, setPassword] = useState("");
    const [password2, setPassword2] = useState("");
    const [email, setEmail] = useState("");
    const [name, setName] = useState("");

    const [pwdDiff, setPwdDiff] = useState(false);
    const [pwdInvalid, setPwdInvalid] = useState(false);

    const [idInvalid, setIdInvalid] = useState(false);
    const [emailInvalid, setEmailInvalid] = useState(false);
    const [nameInvalid, setNameInvalid] = useState(false)
    const [invalidRequest, setInvalidRequest] = useState(false);


    const [idAlreadyExist, setIdAlreadyExist] = useState(false);
    const [emailAlreadyExist, setEmailAlreadyExist] = useState(false);
    const [nameAlreadyExist, setNameAlreadyExist] = useState(false);


    const handleJoin = async (event) => {
        event.preventDefault();
        console.log("----------Join----------");


        axios({
            method: "POST",
            url: "http://localhost:8080/auth/signup",
            data: {
                "username": userId,
                "password": password,
                "nickname": name,
                "mail": email,
            },
        })
            .then(response => handleResponse(response.data))
            .catch(error => handleError(error.response.data)
            );
    }


    const handleError = (error) => {
        console.log(error);
        if (error.message === "Username already exists") {
            setIdAlreadyExist(true);
            setEmailAlreadyExist(false);
            setNameAlreadyExist(false);
        }
        if (error.message === "Email already exists") {
            setIdAlreadyExist(false);
            setEmailAlreadyExist(true);
            setNameAlreadyExist(false);

        }
        if (error.message === "Nickname already exists") {
            setNameAlreadyExist(true);
            setIdAlreadyExist(false);
            setEmailAlreadyExist(false);
        }
    }

    const handleResponse = (response) => {
        console.log(response);
        if (response.message === "SignUp success") {
            navigate("/join/success");
        }
    }


    const handleLogin = (event) => {
        event.preventDefault();
        const isInvalid = pwdInvalid || idInvalid || emailInvalid || pwdDiff;
        if (!isInvalid) {
            console.log("회원가입");
            setInvalidRequest(false);
        } else {
            console.log("회원가입 불가");
            setInvalidRequest(true);
        }
    }

    const passwordEqualChecker = (password2) => {
        if (password === password2) {
            console.log("비밀번호 일치");
            setPwdDiff(false);
        } else {
            console.log("비밀번호 불일치");
            setPwdDiff(true);
        }
    }

    const passwordValidChecker = (password) => {
        let regPass = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$/
        if (regPass.test(password)) {
            console.log("비밀번호 유효");
            setPassword(password);
            setPwdInvalid(false);

        } else {
            console.log("비밀번호 무효");
            setPwdInvalid(true);
        }
    }

    const emailChecker = (email) => {
        const regEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/
        if (regEmail.test(email)) {
            console.log("이메일 유효");
            setEmailInvalid(false);
            setEmail(email)

        } else {
            console.log("이메일 무효");
            setEmailInvalid(true);
        }
    }

    const userIdChecker = (userId) => {
        const regId = /^[a-z]+[a-z0-9]{5,19}$/g;
        if (regId.test(userId)) {
            setIdInvalid(false);
            console.log("아이디 유효");
            setUserId(userId)

        } else {
            console.log("아이디 무효");
            setIdInvalid(true);
        }
    }

    const nameChecker = (name) => {
        const regName = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$/;
        if (regName.test(name)) {
            setNameInvalid(false);
            console.log("닉네임 유효");
            setName(name)
        } else {
            console.log("닉네임 무효");
            setNameInvalid(true);
        }

    }

    return (
        <div className={"container"}>
            <div className={"box"}>
                <form className={"login-form"} onSubmit={handleLogin}>
                    <label htmlFor="id">아이디</label>
                    <input
                        onChange={e => userIdChecker(e.target.value)}
                        type="text" name="id" id="id"/>
                    {idInvalid ? <span className={"warning"}> 아이디가 적합 하지 않습니다</span> : ""}
                    {idAlreadyExist ? <span className={"warning"}> 이미 존재하는 아이디입니다. </span> : ""}

                    <label htmlFor="password1">비밀번호</label>
                    <input
                        onChange={e => passwordValidChecker(e.target.value)}
                        type="password" name="password1" id="password1"/>
                    {pwdInvalid ? <span className={"warning"}> 패스워드가 적합 하지 않습니다</span> : ""}
                    {pwdInvalid ? <span className={"warning"}> 영문/숫자를 조합하여 8~20자 이내로 작성해주세요. </span> : ""}


                    <label htmlFor="password2">비밀번호 확인</label>
                    <input
                        onChange={e => passwordEqualChecker(e.target.value)}
                        type="password" name="password2" id="password2"/>
                    {pwdDiff ? <span className={"warning"}> 패스워드가 일치 하지 않습니다</span> : ""}

                    <label htmlFor="email">이메일</label>
                    <input
                        onChange={e => emailChecker(e.target.value)}
                        type="text" name="email" id="email"/>
                    {emailInvalid ? <span className={"warning"}> 이메일이 적합 하지 않습니다</span> : ""}
                    {emailAlreadyExist ? <span className={"warning"}> 이미 존재하는 이메일입니다. </span> : ""}

                    <label htmlFor="name">닉네임</label>
                    <input
                        onChange={e => nameChecker(e.target.value)}
                        type="text" name="name" id="name"/>
                    {nameInvalid ? <span className={"warning"}> 닉네임이 적합 하지 않습니다</span> : ""}
                    {nameAlreadyExist ? <span className={"warning"}> 이미 존재하는 닉네임입니다. </span> : ""}

                    {invalidRequest ? <span className={"warning"}> 입력을 확인해주세요. </span> : ""}

                    <button className={"main-button"} onClick={handleJoin}>회원가입</button>

                    <Link to="/login">
                        <button>로그인</button>
                    </Link>

                </form>
            </div>
        </div>
    )
}

export default Join;
