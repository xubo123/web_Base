package com.project.resource.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.base.entity.Tree;
@Service("ResourceService")
public class ResourceServiceImpl implements ResourceService {

	@Override
	public void parseTree(List<Tree> trees, List<Tree> treeList) {
		// TODO Auto-generated method stub
		for (Tree tree : treeList) {
			if (tree.getPid() == 0) {
				trees.add(tree);
			}
		}
		for (Tree tree : trees) {
			getChildren(tree, treeList);
		}
	}
	
	private void getChildren(Tree tree, List<Tree> allList) {
		List<Tree> children = getChild(tree.getId(), allList);
		if (children != null && children.size() > 0) {
			tree.setChildren(children);
			for (Tree tree2 : children) {
				getChildren(tree2, allList);
			}
		}
	}
	private List<Tree> getChild(long id, List<Tree> allList) {
		List<Tree> children = new ArrayList<Tree>();
		if (allList != null && allList.size() > 0) {
			for (Tree tree : allList) {
				if (tree.getPid() == id) {
					children.add(tree);
				}
			}
		}
		return children;
	}
}
