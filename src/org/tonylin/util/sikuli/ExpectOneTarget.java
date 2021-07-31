package org.tonylin.util.sikuli;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.sikuli.api.MultiStateTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;


public class ExpectOneTarget extends MultiStateTarget implements Target {
	static private Logger logger = LoggerFactory.getLogger(ExpectOneTarget.class);
	private Map<Target, Object> mTargetMap;
	
	public ExpectOneTarget(){
		mTargetMap = new LinkedHashMap<Target, Object>();
	}
	
	/**
	 * Find target according to the add order.
	 */
	@Override
	protected List<ScreenRegion> getUnordredMatches(ScreenRegion screenRegion) {
		BufferedImage image = screenRegion.capture();

		List<ScreenRegion> allMatches = Lists.newArrayList();
		for (Target t : this.mTargetMap.keySet()) {
			t.setLimit(getLimit());

			List<ScreenRegion> matches = t.doFindAll(screenRegion);
			allMatches.addAll(matches);
			logger.debug("Matches {} for {} ", matches.size(), t);

			if( matches.size() > 0 )
				break;
		}
		logger.debug("All Matches {}", allMatches.size());
		for (ScreenRegion m : allMatches) {
			Rectangle oldBounds = m.getBounds();
			Rectangle newBounds = new Rectangle();
			newBounds.width = (oldBounds.width + 10);
			newBounds.height = (oldBounds.height + 10);
			newBounds.x = (oldBounds.x - 5);
			newBounds.y = (oldBounds.y - 5);
			m.setBounds(newBounds);
		}

		int n = Math.min(getLimit(), allMatches.size());
		List<ScreenRegion> results = allMatches.subList(0, n);

		for (ScreenRegion r : results) {
			r.getStates().putAll(this.mTargetMap);
		}

		return results;
	}

	@Override
	public void addState(Target aTarget, Object aValue){
		mTargetMap.put(aTarget, aValue);
	}
}
