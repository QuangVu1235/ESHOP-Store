package com.project.service.impl;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.repository.OrdersRepo;
import com.project.service.StatsService;

@Service
public class StatsServiceImpl implements StatsService{
	
	@Autowired
	private OrdersRepo repo;

	@Override
	public String[][] getTotalPriceLast6Months(Integer months) {
		String[][] result = new String[2][months];
		YearMonth currentTimes = YearMonth.now();
		for (int i = 0; i < months; i++) {
			String month = currentTimes.minusMonths((long)i).getMonthValue() + "";
			String year = currentTimes.minusMonths((long)i).getYear() + "";
			result[0][(months-1)-i] = month + "-" + year;
			result[1][(months-1)-i] = repo.getToTalPricePerMonth(month, year);
		}
 		return result;
	}

}
