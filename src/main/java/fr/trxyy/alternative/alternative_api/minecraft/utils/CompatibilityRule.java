package fr.trxyy.alternative.alternative_api.minecraft.utils;

import java.util.Map;

public class CompatibilityRule {
	private Action action;
	private OSRestriction os;
	private Map<String, Object> features;

	public CompatibilityRule() {
		this.setAction(Action.allow);
	}

	public CompatibilityRule(CompatibilityRule rule) {
		this.setAction(Action.allow);
		this.setAction(rule.getAction());
		if (rule.os != null) {
			this.setOs(new OSRestriction(rule.getOs()));
		}
		if (rule.features != null) {
			this.features = rule.features;
		}
	}

	public String toString() {
		return "Rule{action=" + this.getAction() + ", os=" + this.getOs() + '}';
	}

	public OSRestriction getOs() {
		return os;
	}

	public void setOs(OSRestriction os) {
		this.os = os;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public enum Action {
		allow("allow", 0), disallow("disallow", 1);

		private Action(final String s, final int n) {
		}
	}

	public static interface FeatureMatcher {
		boolean hasFeature(String param1String, Object param1Object);
	}

	public Action getAppliedAction() {
		if ((this.os != null) && (!this.os.isCurrentOperatingSystem())) {
			return null;
		}
		return this.action;
	}
}