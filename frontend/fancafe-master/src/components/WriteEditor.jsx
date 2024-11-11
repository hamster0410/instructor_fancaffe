import {Editor} from '@toast-ui/react-editor';
import '@toast-ui/editor/toastui-editor.css';
import {forwardRef} from 'react';
import axios from "axios";

let image;
const form = new FormData();

const WriteEditor = forwardRef((props, ref) => {

    const onUploadImage = async (blob, callback) => {
        try {
            console.log(blob);

            let filename
            let imageUrl

            const formData = new FormData();
            formData.append('image', blob);

            axios({
                url: 'http://localhost:8080/file/image-upload',
                method: 'POST',
                data: formData,
            }).then((response) => {
                console.log(response.data);
                imageUrl = `http://localhost:8080/file/image-print?filename=${response.data}`;
                callback(imageUrl, 'image alt attribute');
            }).catch((error) => {
                console.log(error)
            })


        } catch (error) {
            console.error(error);
        }


    };


    return (
        <>
            <Editor
                ref={ref}
                initialValue={props.initialValue ? props.initialValue.contents : ""}
                previewStyle="vertical"
                height="600px"
                initialEditType="wysiwyg"
                useCommandShortcut={false}
                placeholder="글을 작성해주세요."
                hooks={{
                    addImageBlobHook: onUploadImage,
                }}
            />

        </>
    );
});

export default WriteEditor;