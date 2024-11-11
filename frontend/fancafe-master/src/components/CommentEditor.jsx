import instance from "./axios";
import {useEffect, useRef, useState} from "react";
import {Link, useParams} from "react-router-dom";
import "../assets/styles/commentEditor.scss";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faImage, faArrowRightToBracket} from "@fortawesome/free-solid-svg-icons";

const CommentEditor = (props) => {

    const {post_id} = useParams()
    const [newComment, setNewComment] = useState("")
    const [img, setImg] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const inputImageRef = useRef();


    useEffect(() => {
        if (props.comment) {
            // console.log(props.comment.content);
            setNewComment(props.comment.content);

        }
        getLoginInfo();
    }, [])

    const getLoginInfo = () => {

        if (localStorage.getItem("accessToken")) {
            // console.log(localStorage.getItem("accessToken"));
            setIsLoggedIn(true);
        } else {
            setIsLoggedIn(false);
        }
    }

    const onSubmitHandler = (e) => {
        e.preventDefault();
        const formData = new FormData();
        let blob = new Blob([JSON.stringify({content: newComment})], {type: 'application/json'});
        let url = `/comment/create/${post_id}`;
        let method = "post";


        if (props.isEdit) {
            const cid = props.comment.id;
            url = `/comment/update/${cid}`;
            method = "put";
        }

        if (props.isReply) {
            blob = new Blob([JSON.stringify({
                content: newComment,
                parentId: props.parentId
            })], {type: 'application/json'})
        }

        formData.append("commentData", blob);

        if (img != null) {
            formData.append('imageFile', new Blob([img], {type: 'multipart/form-data'}), img.name);
        }


        try {
            instance({
                method: method,
                url: url,
                data: formData,
            }).then((response) => {
                clearInput()

            })
        } catch (error) {
            console.log(error)
        }
    }

    const ImgUploadHandler = async (e) => {
        if (!e.target.files)
            return;
        setImg(e.target.files[0]);
        // console.log(e.target.files[0]);
        console.log("등록");
    }

    const clearInput = () => {
        setNewComment("")
        setImg(null);
        inputImageRef.current.value = null;
        if (props.isReply) {
            props.setReply(false)
        }
        if (props.isEdit) {
            props.setEdit(!props.edit)
        } else {
            props.setCommentSummited(true)
        }


    }

    return (<>
            {
                isLoggedIn ? (
                    <form className="input-comment" onSubmit={(e) => onSubmitHandler(e)}>
                        <textarea value={newComment} onChange={(e) => setNewComment(e.target.value)}/>
                        <div className="btn-section">
                            <label className="input-file-button" htmlFor="input-file"><FontAwesomeIcon
                                icon={faImage}/></label>
                            <input id="input-file" ref={inputImageRef} onChange={(e) => ImgUploadHandler(e)} type="file"
                                   accept="image/*"/>
                            <button className="submit-button">등록</button>
                        </div>
                    </form>

                ) : (<div className={"go-login"}>먼저 로그인 해주세요
                    <Link to="/login">
                        <div className="go-login-button">
                            <div>
                            <FontAwesomeIcon icon={faArrowRightToBracket}/>로그인
                            </div>
                        </div>
                    </Link>
                </div>)
            }
        </>

    )
}
export default CommentEditor