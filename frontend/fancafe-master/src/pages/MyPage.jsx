import {faUserPen} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import "../assets/styles/mypage.scss"
import {useEffect, useState} from "react";
import instance from "../components/axios";
import {useNavigate} from "react-router-dom";

function MyPage(props) {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [name, setName] = useState("");

    const [inputEmail, setInputEmail] = useState("");
    const [inputName, setInputName] = useState("");

    const [nameInvalid, setNameInvalid] = useState(false);
    const [emailInvalid, setEmailInvalid] = useState(false);
    const [invalidRequest, setInvalidRequest] = useState(false);

    const [nameAlreadyExist, setNameAlreadyExist] = useState(false);
    const [emailAlreadyExist, setEmailAlreadyExist] = useState(false);

    const [isModified, setIsModified] = useState(false);

    const [submitted, setSubmitted] = useState(false)
    useEffect(() => {
        const fetchData = async () => {
            const response = await instance({
                url: "/mypage/update",
                method: "GET"
            })
            if (response && response.data) {
                setUsername(response.data.username);

                setName(response.data.nickname);
                setInputName(response.data.nickname);

                setEmail(response.data.mail);
                setInputEmail(response.data.mail);
            }
        }
        fetchData()

    }, [submitted])

    const editUser = (e) => {
        e.preventDefault();
        let data;

        if (inputEmail != email && inputName != name) {
            data = {
                mail: email,
                nickname: name
            }
        } else if (inputName != name) {
            data = {
                nickname: name
            }

        } else if (inputEmail != email) {
            data = {
                mail: email
            }

        } else {
            return
        }


        if (emailInvalid) {
            setInvalidRequest(true)
        } else {
            setInvalidRequest(false)
            instance({
                url: "/mypage/update",
                method: "POST",
                data: data,
            }).then(response => {
                setIsModified(true);
                setSubmitted(!submitted);
            })
                .catch(error => errorHandler(error.response.data.error))

        }
    }
    const errorHandler = (error) => {
        console.log(error);
        // setInvalidRequest(true)
        if (error === "mail already exists") {
            setEmailAlreadyExist(true);
            setNameAlreadyExist(false);

        }
        if (error === "Nickname already exists") {
            setNameAlreadyExist(true);
            setEmailAlreadyExist(false);

        }
    }

    const emailChecker = (email) => {
        const regEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/
        setEmail(email)

        if (regEmail.test(email)) {
            console.log("이메일 유효");
            setEmailInvalid(false);

        } else {
            console.log("이메일 무효");
            setEmailInvalid(true);
        }
    }
    const nameChecker = (name) => {
        const regName = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$/;
        setName(name)

        if (regName.test(name)) {
            setNameInvalid(false);
            console.log("닉네임 유효");
        } else {
            console.log("닉네임 무효");
            setNameInvalid(true);
        }

    }


    const warningCleaner = () => {
        setIsModified(false);
        setNameAlreadyExist(false);
        setEmailAlreadyExist(false);


    }


    function deleteUser() {
        instance({
            method: "DELETE",
            url: "/auth/delete",

        }).then(response => {
            localStorage.clear();
            navigate("/");

        })

    }

    const confirmUser = (e) => {
        e.preventDefault();

        // eslint-disable-next-line no-restricted-globals
        const result = confirm("정말로 탈퇴하실건가요?");
        if (result) {
            // eslint-disable-next-line no-restricted-globals
            const resultAgain = confirm("정말로 정말로 탈퇴하실건가요?");

            if (resultAgain) {
                console.log("회원탈퇴")

                deleteUser();
            } else {
                console.log("탈퇴안함")
            }
        } else {
            console.log("탈퇴안함")
        }
    }

    return (
        <div className="container">
            <div className="box">

                <FontAwesomeIcon className={"icon"} icon={faUserPen}/>
                <form action="post">
                    <span>아이디</span>
                    <span>{username}</span>
                    <hr/>
                    <label htmlFor="email">이메일</label>
                    <input
                        onClick={warningCleaner}
                        onChange={e => emailChecker(e.target.value)}
                        type="text" name="email" id="email" value={email}/>
                    {emailInvalid ? <span className={"warning"}> 이메일이 적합 하지 않습니다</span> : ""}
                    {emailAlreadyExist ? <span className={"warning"}> 이미 존재하는 이메일입니다. </span> : ""}

                    <label htmlFor="name">닉네임</label>
                    <input
                        onClick={warningCleaner}
                        onChange={e => nameChecker(e.target.value)}
                        type="text" name="name" id="name" value={name}/>
                    {nameAlreadyExist ? <span className={"warning"}> 이미 존재하는 닉네임입니다. </span> : ""}
                    {nameInvalid ? <span className={"warning"}> 닉네임이 적합 하지 않습니다</span> : ""}


                    {invalidRequest ? <span className={"warning"}> 입력을 확인해주세요. </span> : ""}
                    {isModified ? <span>수정이 완료되었습니다.</span> : ""}
                    <button onClick={editUser}>저장</button>


                    <button onClick={confirmUser} className="warning-btn">회원탈퇴</button>

                </form>
            </div>

        </div>
    )
}

export default MyPage;