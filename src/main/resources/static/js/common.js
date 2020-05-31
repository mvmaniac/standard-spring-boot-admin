$(document)
.ajaxSend(function (event, jqXHR, settings) {
  // 각 페이지별 beforeSend가 먼저 호출 됨...
  // jquery 에서 자동으로 추가해주나, 명시적으로 추가함...
  jqXHR.setRequestHeader('X-Requested-With', 'XMLHttpRequest');

  if (!/^(GET|HEAD|OPTIONS|TRACE)/i.test(settings.type)) {
    jqXHR.setRequestHeader(
        $('meta[name="_csrf_header"]').attr('content'),
        $('meta[name="_csrf_token"]').attr('content')
    );
  }
})
;

function alertAndFocus($element, message) {
  alert(message);
  $element.focus();
}
