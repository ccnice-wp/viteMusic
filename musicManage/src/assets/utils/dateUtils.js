import moment from "moment";
 
function dateFormat(data) {
 
    return moment(new Date(data).getTime()).format('YYYY-MM-DD HH:mm');;
 
}
 

export default dateFormat