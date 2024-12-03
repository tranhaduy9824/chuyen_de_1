var cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
function addItemIntoCart(item){
	const oldItemIndex=cartItems.indexOf(item);
	if(oldItemIndex){
		cartItems[oldItemIndex].quantity+=1
	}else{
		cartItems.push(item)
	}
	localStorage.setItem('cartItems',JSON.stringify(cartItems))
}
function removeCartItem(itemId){
	cartItems=cartItems.filter(item => item.id != itemId);
	localStorage.setItem('cartItems',JSON.stringify(cartItems))
}
function updateCartItem(item){
	const oldItemIndex=cartItems.indexOf(item);
	if(oldItemIndex){
		cartItems[oldItemIndex]=item
	}else{
		cartItems.push(item)
	}
	localStorage.setItem('cartItems',JSON.stringify(cartItems))
}
function removeListItem(itemIds){
	cartItems=cartItems.filter(item => !itemIds.includes(item.id));
	localStorage.setItem('cartItems',JSON.stringify(cartItems))
}
$( "#add_cart" ).submit(function( event ) {
  	event.preventDefault();
	var data = {optionIds:[],quantity:0};
	$.each($(this).serializeArray(), function(i, field) {
	    if(field.name!='quantity')
	    	data[field.name].push(field.value);
	    else
	    	data[field.name]=field.value
	});
	details=sessionStorage.getItem("details")
	console.log(details)
	
	detail=details.find((element) => {
		    if (element.options.length !== data['optionIds'].length) return false;

		    for (var i = 0; i < element.options.length; i++) {
		      if (element.options[i].id !== data['optionIds'][i]) {
		        return false;
		      }
		    }
	})
	console.log(detail)
});
