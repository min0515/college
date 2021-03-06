package com.naver.minsee1234;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.naver.minsee1234.dao.BoardDao;
import com.naver.minsee1234.dao.GoodsDao;
import com.naver.minsee1234.entities.Board;
import com.naver.minsee1234.entities.Goods;
import com.naver.minsee1234.entities.GoodsPaging;
import com.naver.minsee1234.entities.Tb_cart;

@Controller
public class GoodsController {

	@Autowired
	private SqlSession sqlSession;
	@Autowired
	Goods goods;
	@Autowired
	GoodsPaging goodspaging;
	public static String find;

	@RequestMapping(value = "/qnaBoardWriteSave", method = RequestMethod.POST)
	public String qnaBoardWriteSave(Model model, @ModelAttribute Board board,
			@RequestParam("b_attachfile") MultipartFile b_attachfile, HttpServletRequest request) throws Exception {
		String filename = b_attachfile.getOriginalFilename();
		String path = "F:/SPRINGBOOTSOURCE/eyeconspringboot (1)/src/main/resources/static/uploadattachs/";
		String realpath = "/uploadattachs/";
		if (!filename.equals("")) {
			byte bytes[] = b_attachfile.getBytes();
			try {
				BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(path + filename));
				output.write(bytes);
				output.flush();
				output.close();
				board.setB_attach(realpath + filename);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
//		String ip = getIp(request);
//		board.setB_inputip(ip);
		SimpleDateFormat df = new SimpleDateFormat("yyyy??? MM??? dd??? hh??? mm:ss");
		Date date = new Date();
		String today = df.format(date);
		board.setB_inputtime(today);
		BoardDao dao = sqlSession.getMapper(BoardDao.class);
		dao.insertRow(board);

		return "goods/board_qna";
	}

//	@RequestMapping(value = "/goodsManageList", method = RequestMethod.GET)
//	public String goodsManageList(Locale locale, Model model) throws Exception {
//		GoodsDao dao = sqlSession.getMapper(GoodsDao.class);
//		int pagesize = 10;
//		int page = 1;
//		int startrow = (page - 1) * pagesize;
//		int endrow = 10;
//
//		goodspaging.setFind(this.find);
//		if (goodspaging.getFind() == null) {
//			goodspaging.setFind("");
//		}
//
//		goodspaging.setStartrow(startrow);
//		goodspaging.setEndrow(endrow);
//		int rowcount = dao.goodsSelectCountFirst(goodspaging);
//		int absPage = 1;
//
//		if (rowcount % pagesize == 0) {
//			absPage = 0;
//		}
//		int pagecount = rowcount / pagesize + absPage;
//		int pages[] = new int[pagecount];
//		for (int i = 0; i < pagecount; i++) {
//			pages[i] = i + 1;
//		}
//
//		ArrayList<Goods> goodses = dao.goodsSelectPageList(goodspaging);
//
//		model.addAttribute("goodses", goodses);
//		model.addAttribute("pages", pages);
//		return "goods/goods_page_list";
//	}

	@RequestMapping(value = "/qnaWrite", method = RequestMethod.GET)
	public String boardWrite(Locale locale, Model model) {
		return "goods/qna_write";
	}

	@RequestMapping(value = "/shoppingAboutus", method = RequestMethod.GET)
	public String shoppingAboutus(Model model) throws Exception {
		return "goods/shopping_aboutus";
	}

	@RequestMapping(value = "/boardReview", method = RequestMethod.GET)
	public String boardReview(Model model) throws Exception {
		return "goods/board_review";
	}

	@RequestMapping(value = "/boardQna", method = RequestMethod.GET)
	public String boardQna(Model model) throws Exception {
		return "goods/board_qna";
	}

	@RequestMapping(value = "/manageCancel", method = RequestMethod.GET)
	public String manageCancel(Model model) throws Exception {
		return "goods/manage_cancel";
	}

	@RequestMapping(value = "/manageGoodsList", method = RequestMethod.GET)
	public String manageGoodsList(Model model) throws Exception {
		return "goods/manage_goods";
	}

	@RequestMapping(value = "/manageOrder", method = RequestMethod.GET)
	public String manageOrder(Model model) throws Exception {
		return "goods/manage_order";
	}

	@RequestMapping(value = "/myPage", method = RequestMethod.GET)
	public String myPage(Model model) throws Exception {
		return "goods/mypage";
	}

	@RequestMapping(value = "/myPageOrderList", method = RequestMethod.GET)
	public String myPageOrderList(Model model) throws Exception {
		return "goods/mypage_order_list";
	}

	@RequestMapping(value = "/paymentComplete", method = RequestMethod.GET)
	public String paymentComplete(Model model) throws Exception {
		return "goods/payment_complete";
	}

	@RequestMapping(value = "/goodsPageList", method = RequestMethod.GET)
	public String goodsPageList(Locale locale, Model model) throws Exception {
		GoodsDao dao = sqlSession.getMapper(GoodsDao.class);
		int pagesize = 10;
		int page = 1;
		int startrow = (page - 1) * pagesize;
		int endrow = 10;

		goodspaging.setFind(this.find);
		if (goodspaging.getFind() == null) {
			goodspaging.setFind("");
		}

		goodspaging.setStartrow(startrow);
		goodspaging.setEndrow(endrow);
		int rowcount = dao.goodsSelectCountFirst(goodspaging);
		int absPage = 1;

		if (rowcount % pagesize == 0) {
			absPage = 0;
		}
		int pagecount = rowcount / pagesize + absPage;
		int pages[] = new int[pagecount];
		for (int i = 0; i < pagecount; i++) {
			pages[i] = i + 1;
		}

		ArrayList<Goods> goodses = dao.goodsSelectPageList(goodspaging);

		model.addAttribute("goodses", goodses);
		model.addAttribute("pages", pages);
		return "goods/goods_page_list";
	}

	@RequestMapping(value = "/goodsDelivery", method = RequestMethod.GET)
	public String delivery(Model model, @RequestParam int g_seq, HttpSession session) throws Exception {
		GoodsDao dao = sqlSession.getMapper(GoodsDao.class);
		goods = dao.goodsSelectOne(g_seq);
		System.out.println(g_seq);
		model.addAttribute("goods", goods);
		return "goods/payment";
	}

	@RequestMapping(value = "/goodsDetail2", method = RequestMethod.GET)
	public String goodsDetail2(Model model, @RequestParam int g_seq, HttpSession session) throws Exception {
		GoodsDao dao = sqlSession.getMapper(GoodsDao.class);
		goods = dao.goodsSelectOne(g_seq);
//		System.out.println(g_seq);
		model.addAttribute("goods", goods);
		return "goods/goods_detail2";
	}

	@RequestMapping(value = "/goodsWrite", method = RequestMethod.GET)
	public String goodsWrite(Locale locale, Model model) {
		return "goods/goods_write";
	}

	@RequestMapping(value = "/goodsWriteSave", method = RequestMethod.POST)
	public String goodsWriteSave(Model model, @ModelAttribute Goods goods,
			@RequestParam("g_attachfile") MultipartFile g_attachfile, MultipartHttpServletRequest request)
			throws Exception {
		List<MultipartFile> fileList = request.getFiles("g_attachfile");
		String path = "D:/util/college/src/main/resources/static/uploadattachs/";
		String filename = g_attachfile.getOriginalFilename();
		String realpath = "uploadattachs/"; // server path
		for (MultipartFile mf : fileList) {
			String originFileName = mf.getOriginalFilename(); // ?????? ?????? ???
			long fileSize = mf.getSize(); // ?????? ?????????
			String safeFile = path + originFileName;
			try {
				mf.transferTo(new File(safeFile));
				goods.setG_attach(realpath + filename);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy??? MM??? dd??? hh??? mm:ss");
		Date date = new Date();
		String today = df.format(date);
		GoodsDao dao = sqlSession.getMapper(GoodsDao.class);
		dao.goodsInsertRow(goods);

		return "index";
	}

	/* ???????????? ?????? ?????? */
	@RequestMapping(value = "/goodsCartIn", method = RequestMethod.POST)
	@ResponseBody
	public String goodsCartIn(Model model, Tb_cart tb_cart, HttpSession session) throws Exception {
		GoodsDao dao = sqlSession.getMapper(GoodsDao.class);
		String member_id = (String) session.getAttribute("sessionMember_id");
		if (member_id == null) {
			return "login/login";
		} else {
			tb_cart.setMember_id(member_id);
			tb_cart.setTotalprice(tb_cart.getPrice() * tb_cart.getQty());
			try {
				dao.cartInsertRow(tb_cart);
				return "y";
			} catch (Exception e) {
				dao.cartUpdateRow(tb_cart);
				return "y";
			}

		}

	}

	@RequestMapping(value = "/goodsCartGo", method = RequestMethod.GET)
	public String goodsCartGo(Model model, HttpSession session) throws Exception {
		GoodsDao dao = sqlSession.getMapper(GoodsDao.class);
		String member_id = (String) session.getAttribute("sessionMember_id");
		if (member_id == null) {
			return "login/login";
		} else {
			int mycartcount = dao.myGoodsCartCount(member_id);
			ArrayList<Tb_cart> myProducts = dao.myGoodsCartSelect(member_id);
			model.addAttribute("mycartcount", mycartcount);
			model.addAttribute("myProducts", myProducts);
			return "goods/goods_cart";
		}

	}

}
