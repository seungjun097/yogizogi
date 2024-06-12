/* 다음 주소 연동 */
function execution_daum_address() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일 때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 주소 변수 문자열과 참고항목 문자열 합치기
                addr += extraAddr;
            } else {
                addr += ' ';
            }

            // 주소를 쿠키에 저장
            setCookie("deliveryAddress1", data.zonecode, 30);
            setCookie("deliveryAddress2", addr, 30);

            // 입력된 주소를 화면에 표시
            $("#deliveryAddress1").val(data.zonecode);
            $("#deliveryAddress2").val(addr);
        }
    }).open();
}

// 쿠키에 주소 저장 function
var setCookie = function(name, value, exp) {
    var date = new Date();
    date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000);
    document.cookie = name + '=' + encodeURIComponent(value) + ';expires=' + date.toUTCString() + ';path=/';
};

// 쿠키에서 주소 읽어오기 function
var getCookie = function(name) {
    var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value ? decodeURIComponent(value[2]) : null;
};

$(document).ready(function() {
    //페이지 로딩 시 쿠키에 있는 주소값 입력
    $("#deliveryAddress1").val(getCookie("deliveryAddress1"));
    $("#deliveryAddress2").val(getCookie("deliveryAddress2"));

    //주소 변경 시 페이지 이동
    $("#deliveryAddress2").on('input', function() {
        let address1 = getCookie("deliveryAddress1");
        location.href = "/store/DEFAULT/" + address1 + "/none/none";
    })
    //카테고리 버튼 클릭 시
    $(".category_link").on('click', function() {

        let address1 = getCookie("deliveryAddress1");
        category = $(this).data('value');
        location.href = "/store/" + category + "/" + address1 + "/" + sort + "/" + search;
    });

    //정렬 버튼 클릭 시
    $(".sort-link").on('click', function() {
        let address1 = getCookie("deliveryAddress1");
        sort = $(this).data('value');
        location.href = "/store/" + category + "/" + address1 + "/" + sort + "/" + search;
    });

    //검색 실행 시
    $("#searchBtn").on('click', function() {
        let address1 = getCookie("deliveryAddress1");
        search = $("#nameSearch").val();
        location.href = "/store/" + category + "/" + address1 + "/" + sort + "/" + search;
    });
});



