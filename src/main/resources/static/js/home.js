/* 다음 주소 연동 */
function execution_daum_address() {
    new daum.Postcode({
        oncomplete : function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', '
                        + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 추가해야할 코드
                // 주소변수 문자열과 참고항목 문자열 합치기
                addr += extraAddr;

            } else {
                addr += ' ';
            }

            sessionStorage.setItem('deliveryAddress1', data.zonecode);
            sessionStorage.setItem('deliveryAddress2', addr);

            $("#deliveryAddress1").val(data.zonecode);
            $("#deliveryAddress2").val(addr);

        }

    }).open();

}

//페이지 진입시 쿠키에서 주소정보 읽어오기
$("#deliveryAddress1").val(sessionStorage.getItem('deliveryAddress1'));
$("#deliveryAddress2").val(sessionStorage.getItem('deliveryAddress2'));

//카테고리 아이콘 클릭 시 검색 이동
$(".category li").click(function(){
    let address1 = $("#deliveryAddress1").val();
    if (address1 != null) {
        swal("배달 받으실 주소를 입력해 주세요");
        return false;
    }

    // 클릭한 li 요소의 텍스트 값 가져오기
    let categoryType = $(this).text().trim();

    // categoryType을 이용하여 주소 설정
    let enumType;
    switch (categoryType) {
        case "KOREAN":
            enumType = "KOREAN";
            break;
        case "SUSHI":
            enumType = "SUSHI";
            break;
        case "THAI":
            enumType = "THAI";
            break;
        case "HOTDOT":
            enumType = "HOTDOT";
            break;
        case "ITALIAN":
            enumType = "ITALIAN";
            break;
        case "PIZZA":
            enumType = "PIZZA";
            break;
        case "MAXICAN":
            enumType = "MAXICAN";
            break;
        case "RAMAN":
            enumType = "RAMAN";
            break;
        case "FASTFOOD":
            enumType = "FASTFOOD";
            break;
        case "BBQ":
            enumType = "BBQ";
            break;
        case "TEA":
            enumType = "TEA";
            break;
        case "STREET":
            enumType = "STREET";
            break;
        case "CHICKEN":
            enumType = "CHICKEN";
            break;
        case "SANDWICH":
            enumType = "SANDWICH";
            break;
        case "OTHER_CATEGORY":
            enumType = "OTHER_CATEGORY";
            break;
    }

    // 주소 설정 및 페이지 이동
    location.href = "/store/" + enumType + "/" + address1;
})




