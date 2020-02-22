package fr.trxyy.alternative.alternative_api.minecraft;

import java.util.Map;

public class CompatibilityRule {
	private Action action;
	private OSRestriction os;
//	private HashMap<String, String> os;
	private Map<String, Object> features; // private HashMap<String, String> features;

	public CompatibilityRule() {
		this.setAction(Action.allow);
	}

	public CompatibilityRule(CompatibilityRule rule) {
		this.setAction(Action.allow);
		this.setAction(rule.getAction());
		if (rule.os != null) {
			this.setOs(new OSRestriction(rule.getOs()));
//			this.os = rule.os;
		}
		if (rule.features != null) {
			this.features = rule.features;
		}
	}

//	public Action getAppliedAction(FeatureMatcher featureMatcher) {
//		if ((this.getOs() != null) && (!this.getOs().isCurrentOperatingSystem())) {
//			return null;
//		}
//		if (this.features != null) {
//			if (featureMatcher == null) {
//				return null;
//			}
//			for (Map.Entry<String, String> feature : this.features.entrySet()) {
//				if (!featureMatcher.hasFeature((String) feature.getKey(), feature.getValue())) {
//					return null;
//				}
//			}
//		}
//		return this.getAction();
//	}

	public String toString() {
		return "Rule{action=" + this.getAction() + ", os=" + this.getOs() + '}';
	}

	public OSRestriction getOs() {
		return os;
	}

	public void setOs(OSRestriction os) {
		this.os = os;
	}

//	public HashMap<String, String> getOs() {
//		return this.os;
//	}
//
//	public void setOs(final HashMap<String, String> os) {
//		this.os = os;
//	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

    public enum Action
    {
        allow("allow", 0), 
        disallow("disallow", 1);
        
        private Action(final String s, final int n) {
        }
    }
    
    public static interface FeatureMatcher
    {
      boolean hasFeature(String param1String, Object param1Object);
    }

    public Action getAppliedAction()
    {
      if ((this.os != null) && (!this.os.isCurrentOperatingSystem())) {
        return null;
      }
      return this.action;
    }
}
