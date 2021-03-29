package com.naver.minsee1234.dao;

import com.naver.minsee1234.entities.QnaBoard;

public interface QnaBoardDao {
	public QnaBoard qnaSelectOne(int qna_seq) throws Exception;
}