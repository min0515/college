$(document).ready(function() {

	$('#qtyInputProdetail').on('change', function() {
		var qty = $('#qtyInputProdetail').val();
		var price = $('#priceHiddenDetail').val();
		var result = price * qty;
		$('#goodsDetailTotalPrice').text(result + "원");
		$('#detailTotalQty').text("상품금액(" + qty + "개)");
	});

	$('#detailCartGo').on('click', function() {
		var tb_cart = $('#goodsDetailform').serialize();
		$.ajax({
			type: 'POST',
			data: tb_cart,
			datatype: 'json',
			url: 'goodsCartIn',
			success: function(data) {
				if (data == "login") {
					location.href = "login";
				} else {
					$('.ui.goodsdetailCart.modal').modal('show');
				}
			},
			error: function(xhr, status, error) {
				alert('ajax error' + xhr.status);
			}

		});
	});

	$('#cartGoBtn').on('click', function() {
		$('.ui.goodsdetailCart.modal').modal('hide');
		location.href = "goodsCartGo";
	});

	$("#myCartTable").on('change', '#checkmycart', 'td', function() {
		var row = $(this).closest('tr');
		var td = row.children();
		var input = td.eq(0).children().children();
		var qty = td.eq(3).children().val();
		var deliveryVal = Number(td.eq(6).children().val());
		var totalPriceVal = Number(td.eq(7).children().val())*qty;
		var result = deliveryVal + totalPriceVal;
		if ($(input).is(":checked") == true) {
			$('#productPrice').text(Number($('#productPriceVal').val()) + totalPriceVal);
			var productPriceRe = $('#productPrice').text().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			$('#productPrice').text(productPriceRe)
			$('#productPriceVal').val(Number($('#productPriceVal').val()) + totalPriceVal);

			$('#deliveryPrice').text(Number($('#deliveryPriceVal').val()) + deliveryVal);
			var deliveryPriceRe = $('#deliveryPrice').text().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			$('#deliveryPrice').text(deliveryPriceRe)
			$('#deliveryPriceVal').val(Number($('#deliveryPriceVal').val()) + deliveryVal);

			$('#finalPrice').text(Number($('#finalPriceVal').val()) + result);
			var finalPriceRe = $('#finalPrice').text().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			$('#finalPrice').text(finalPriceRe)
			$('#finalPriceVal').val(Number($('#finalPriceVal').val()) + result);

		} else {
			$('#productPrice').text(Number($('#productPriceVal').val()) - totalPriceVal);
			var productPriceRe = $('#productPrice').text().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			$('#productPrice').text(productPriceRe)
			$('#productPriceVal').val(Number($('#productPriceVal').val()) - totalPriceVal);

			$('#deliveryPrice').text(Number($('#deliveryPriceVal').val()) - deliveryVal);
			var deliveryPriceRe = $('#deliveryPrice').text().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			$('#deliveryPrice').text(deliveryPriceRe)
			$('#deliveryPriceVal').val(Number($('#deliveryPriceVal').val()) - deliveryVal);

			$('#finalPrice').text(Number($('#finalPriceVal').val()) - result);
			var finalPriceRe = $('#finalPrice').text().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			$('#finalPrice').text(finalPriceRe)
			$('#finalPriceVal').val(Number($('#finalPriceVal').val()) - result);
		}
	});
	
	
	
	$("#myCartTable").on('change', '#qtyInputProdetail', 'td', function() {
		var row = $(this).closest('tr');
		var td = row.children();
		var qty = td.eq(3).children().val();
		var beforeQty = td.eq(8).children().val();
		var input = td.eq(0).children().children();
		var totalPriceVal = Number(td.eq(7).children().val());
		if ($(input).is(":checked") == true) {
			$('#productPriceVal').val(Number($('#productPriceVal').val()) - beforeQty*totalPriceVal + qty*totalPriceVal);
			$('#productPrice').text(Number($('#productPriceVal').val()));
			var productPriceRe = $('#productPrice').text().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			$('#productPrice').text(productPriceRe)
			$('#finalPriceVal').val(Number($('#productPriceVal').val()) + Number($('#deliveryPriceVal').val()));
			$('#finalPrice').text(Number($('#finalPriceVal').val()));
			var finalPriceRe = $('#finalPrice').text().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			$('#finalPrice').text(finalPriceRe);
			td.eq(8).children().val(qty);
		} else {
			td.eq(8).children().val(qty);
		}
	});

	
	
	


});