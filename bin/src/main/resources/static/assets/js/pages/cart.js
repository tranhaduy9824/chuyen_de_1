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
function removeItems(){
	
}