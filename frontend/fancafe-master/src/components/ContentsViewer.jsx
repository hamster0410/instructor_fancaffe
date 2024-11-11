import {useEffect, useState} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import instance from './axios.jsx'
import {Viewer} from '@toast-ui/react-editor';
import HeartButton from './HeartButton.jsx';
import Comments from "./Comments";
import {useModifyTime} from '../utils/useModifyTime.jsx';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faFaceGrinTears, faPenToSquare} from "@fortawesome/free-regular-svg-icons";
import {faShare, faXmark} from "@fortawesome/free-solid-svg-icons";
import "../assets/styles/contentViewer.scss"

const ContentsViewer = ({props}) => {


    const {category, post_id} = useParams();
    const [contentInfo, setContentInfo] = useState(null);
    const [isAuth, setIsAuth] = useState(false);
    const [heartStatus, setHeartStatus] = useState(contentInfo?.heart);
    const [countHeart, setCountHeart] = useState(contentInfo?.count_heart);
    const modifiedTime = useModifyTime(contentInfo?.createdDate);

    useEffect(() => {
        instance({
            method: "GET",
            url: `/${category}/${post_id}`
        })
            .then(response => {
                setContentInfo(response.data)
                setHeartStatus(response.data.heart)
                setCountHeart(response.data.count_heart)
            })
            .catch(error => console.error(error));

        instance({
            method: "GET",
            url: `/${category}/${post_id}/edit`
        })
            .then(response => {
                console.log(response.data);
                if (response.data === true) {
                    setIsAuth(true)
                } else {
                    setIsAuth(false)
                }
            })
            .catch(error => console.error(error));

    }, [category, post_id]);
    const navigate = useNavigate();

    if (!contentInfo) {
        return (<p>데이터가 없습니다</p>)
    }


    console.log('데이터: ', contentInfo);

    const editHandler = () => {
        navigate("/post/write", {state: contentInfo});
    }

    const deleteHandler = () => {
        instance({
            method: "DELETE",
            url: `/${category}/${post_id}/delete`

        }).then(response => {
            navigate("/");
        }).catch(error => console.error(error));

    }

    return (
        <div>
            <div className="content-info-container">
                <div className="info-box">
                        <div className="content-info">
                            <h2> {contentInfo.category}</h2>
                            <h1> {contentInfo.title}</h1>
                        </div>
                        <div className="content-author-info">
                            <span className="user-name">{contentInfo.nickname}{" "}</span>
                            <FontAwesomeIcon icon={faFaceGrinTears} className="faFaceGrinTears"/><span className="hearts">{countHeart}</span>
                            <span className="modified-time"> {modifiedTime}{" "}·{" "}</span>
                            <span className="hits">조회 {contentInfo.hits}</span>
                        </div>
                </div>
                <div className="content-modify-button">
                    {isAuth ? (<>
                        <button onClick={editHandler}><FontAwesomeIcon icon={faPenToSquare}/>수정</button>
                        <button onClick={deleteHandler}><FontAwesomeIcon className="fa-xmark" icon={faXmark}/>삭제</button>
                    </>) : null}
                    <button><FontAwesomeIcon icon={faShare} />공유</button>
                </div>
 
            </div>

            <hr/>

            <div className="view-content">
                <Viewer
                    initialValue={contentInfo.contents}
                />
            </div>
 

            <div className="heart-button-container">
                <HeartButton
                    heartStatus={heartStatus}
                    setHeartStatus={setHeartStatus}
                    setCountHeart={setCountHeart}
                    countHeart={countHeart}
                />
            </div>
 

            <hr/>
            <Comments/>
        </div>
    );
};

export default ContentsViewer;