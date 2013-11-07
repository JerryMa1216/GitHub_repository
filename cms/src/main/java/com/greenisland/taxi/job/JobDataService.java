package com.greenisland.taxi.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bstek.bdf2.job.model.JobDefinition;
import com.bstek.bdf2.job.service.IJobDataService;
import com.bstek.dorado.core.Configure;

/**
 * IJobDataService实现类
 * 
 * @author Jerry
 * @E-mail jerry.ma@bstek.com
 * @version 2013-8-2下午4:08:58
 */
@Service
public class JobDataService implements IJobDataService {
	private String jobApplicationName = Configure.getString("bdf2.jobApplicationName");

	public String getCompanyId() {
		return jobApplicationName;
	}

	public List<JobDefinition> filterJobs(List<JobDefinition> jobs) {
		List<JobDefinition> tempJobs = new ArrayList<JobDefinition>();
		for (JobDefinition jobDef : jobs) {
			if (jobDef.getCompanyId().toLowerCase().equals(jobApplicationName.toLowerCase())) {
				tempJobs.add(jobDef);
			}
		}
		return tempJobs;
	}

}
