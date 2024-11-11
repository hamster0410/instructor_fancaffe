import {useCallback, useEffect, useRef, useState} from "react"
import WriteEditor from "../components/WriteEditor"
import '../assets/styles/postWrite.scss'
import {useLocation, useNavigate} from "react-router-dom";
import instance from "../components/axios";


export default function PostWrite(props) {
    const navigate = useNavigate();
    // 작성 내용(contents), 제목(title), 카테고리(category) 저장
    const editorRef = useRef(null);
    const {state} = useLocation();

    const [title, setTitle] = useState('');
    // 카테고리 선택 최상단에 자유가 있으므로 디폴트 값으로 자유
    const [category, setCategory] = useState('free');
    const [isEdit, setIsEdit] = useState(false);


    useEffect(() => {
        if (state) {

            console.log("수정");
            console.log(state);

            setIsEdit(true);
            setTitle(state.title);
            setCategory(state.category);

        } else {
            setIsEdit(false);
        }
    }, [])


    // 카테고리 선택할 때 저장
    function onSelectCategory(e) {
        //   console.log(e.target.value); // 테스트 코드
        setCategory(e.target.value);
    }

    // 제출할 때 db에 post
    const onClickEnrollBtn = useCallback(async () => {
        const markdown = editorRef.current.getInstance().getMarkdown(); // contents 가져오기
        // 타이틀 없을 때도 경고창
        // navigate 사용시 button의 내용이 일시적으로 사라지는 문제 발생 (추후에 해결해볼게요)
        if (!markdown) {
            alert('내용을 작성해주세요.');

        } else {
            const token = localStorage.getItem('accessToken'); // 토큰 가져오기
            const url = isEdit ? `/${state.category}/${state.id}/edit` : "/post/write"
            const method = isEdit ? "PUT" : "POST";
            // const finalTitle = isEdit ? state.title : title;
            // const url = "";

            // 보낼 데이터
            const data = {
                "title": title,
                "contents": markdown,
                "category": category
            }


            instance({
                method: method,
                url: url,
                data: data,
            })
                .then(() => {
                    navigate('/');
                })
                .catch(error => console.error(error));
        }
    }, [category, title]);

    return (
        <div>
            <select name='category' value={category}
                    onChange={(e) => onSelectCategory(e)}
                    className="selectorCategory">
                <option value='free'>자유</option>
                <option value='question'>강사님께 질문</option>
                <option value='ootd'>강사님 OOTD</option>
                <option value='fanart'>강사님 팬아트</option>
                <option value='comment'>강사님께 한마디</option>
                <option value='graduate'>졸업생 커뮤니티</option>
                <option value='daily'>데일리 코테</option>
            </select>
            <input placeholder='제목' name='title'
                   className="inputTitle"
                   value={title}
                   onChange={(e) => setTitle(e.target.value)}
            />
            <WriteEditor ref={editorRef} initialValue={state}/>
            <button onClick={onClickEnrollBtn}
                    className="submitButton">등록
            </button>
        </div>
    )
}