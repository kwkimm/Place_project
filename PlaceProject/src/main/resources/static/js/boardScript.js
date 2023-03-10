//쿼리셀렉터로 꺼내와야함 - 삭제버튼
const btns = document.querySelectorAll(".delete");

btns.forEach(function(btn){
	btn.addEventListener('click', function(){
	
		if(confirm('정말로 삭제하시겠습니까?')){
			location.href = this.dataset.uri;
		}
	
	});
});


const pageBtns = document.querySelectorAll('.page-link');
const searchBtn = document.querySelector('#btn_search');

pageBtns.forEach(function(btn){
	
	btn.addEventListener('click', function(){
		
		document.querySelector('#page').value = this.dataset.page;
		document.querySelector('#searchForm').submit();
		
	});
	
});




searchBtn.addEventListener('click',function(e){
	
	e.preventDefault;
	
	
	document.querySelector('#searchType').value = document.querySelector('#s_type').value;
	document.querySelector('#kw').value = document.querySelector('#search_kw').value;
	
	// 다른페이지에 있다가 검색 실행 시, 검색 된 결과 페이지가 1번부터 나와야 하므로 0으로 만들어줌
	document.querySelector('#page').value = 0;
	
	document.querySelector('#searchForm').submit();
	
});



// 최신순, 조회순, 좋아요순
const sortBtn = document.querySelectorAll('.btn-sort');

sortBtn.forEach(function(btn){
	btn.addEventListener('click',function(){
		document.querySelector('#sort').value = this.dataset.sort;
		console.log(document.querySelector('#sort').value);
		document.querySelector('#searchForm').submit();
	});
});