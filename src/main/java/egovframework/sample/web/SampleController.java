package egovframework.sample.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import egovframework.sample.service.SampleService;
import egovframework.sample.service.SampleVO;

@Controller
@SessionAttributes("sample")
public class SampleController {
	
	@Resource(name="sampleService")
	private SampleService sampleService;
	
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<>();
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");
		return conditionMap;
	}
	
	@RequestMapping(value="/insertSample.do", method=RequestMethod.GET)
	public String insertSampleView() throws Exception {
		System.out.println("등록 화면으로 이동");
		return "insertSample";
	}
	
	@RequestMapping(value="/insertSample.do", method=RequestMethod.POST)
	public String insertSample(SampleVO vo) throws Exception {
		System.out.println("샘플 등록 처리");
		sampleService.insertSample(vo);
		return "redirect:/selectSampleList.do";
	}
	
	@RequestMapping("/updateSample.do")
	public String updateSample(@ModelAttribute("sample") SampleVO vo) throws Exception {
		sampleService.updateSample(vo);
		return "redirect:/selectSampleList.do";
	}
	
	@RequestMapping("/deleteSample.do")
	public String deleteSample(SampleVO vo) throws Exception {
		sampleService.deleteSample(vo);
		return "redirect:/selectSampleList.do";
	}
	
	@RequestMapping("/selectSample.do")
	public String selectSample(SampleVO vo, Model model) throws Exception {
		model.addAttribute("sample", sampleService.selectSample(vo));
		return "selectSample";
	}
	
	@RequestMapping(value="/selectSampleList.do")
	public String selectSampleList(SampleVO vo, Model model) throws Exception {
		// Null Check
		if(vo.getSearchCondition() == null) {vo.setSearchCondition("TITLE");}
		if(vo.getSearchKeyword() == null) {vo.setSearchKeyword("");}

		model.addAttribute("sampleList",sampleService.selectSampleList(vo));
		return "selectSampleList";
	}
	
}
