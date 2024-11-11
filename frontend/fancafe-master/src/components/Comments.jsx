import axios from "axios";
import {useEffect, useState} from "react";
import Comment from "./Comment";
import {useParams} from "react-router-dom";
import CommentEditor from "./CommentEditor";

const Comments = () => {
    const {post_id} = useParams()
    const [data, setData] = useState("")
    const [commentSummited, setCommentSummited] = useState(false);
    const [parent, setParent] = useState(null)

    useEffect(() => {
        // console.log("postid =" + post_id);
        axios({
            method: 'get',
            url: `http://localhost:8080/comment/${post_id}`,
        }).then(({data}) => {
            console.log(data)
            setData(data.comments)
            replySort(data.comments)
        }).catch((error) => {
            console.log(error)
        });
        setCommentSummited(false);
    }, [commentSummited])


    const replySort = (data) => {
        const map = new Map();
        const finalList = new Set();
        const parentList = new Set();


        data.forEach((item) => {
            map.set(item.id, []);
            if (item.parent?.id) {
                const children = map.get(item.parent.id) || [];
                children.push(item.id);
                map.set(item.parent.id, children);
            } else {
                parentList.add(item.id);
            }
        });

        const addToFinalList = (id) => {
            if (finalList.has(id)) return;
            finalList.add(id);
            const children = map.get(id) || [];
            children.forEach((childId) => addToFinalList(childId));
        };

        data.forEach((item) => {
            if (!item.parent) {
                addToFinalList(item.id);
            }
        });

        const order = Array.from(finalList);
        const sortedData = data.sort((a, b) => order.indexOf(a.id) - order.indexOf(b.id));

        // console.log("정렬된 data:", sortedData);
        // console.log(parentList);
        setParent(parentList);

        return sortedData;
    };


    return (<>

        {/*{data ? console.log(data) : null}*/}
        {data ? data.map((comment) => {
            return (<Comment key={comment.id} commentSummited={commentSummited} setCommentSummited={setCommentSummited}
                             parentList={parent} comment={comment}> </Comment>)
        }) : null}

        <CommentEditor commentSummited={commentSummited} setCommentSummited={setCommentSummited}></CommentEditor>


    </>)
}

export default Comments;